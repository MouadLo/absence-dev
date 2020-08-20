import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './matiere.reducer';
import { IMatiere } from 'app/shared/model/matiere.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IMatiereDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const MatiereDetail = (props: IMatiereDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { matiereEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="intranetApp.matiere.detail.title">Matiere</Translate> [<b>{matiereEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="idMatiere">
              <Translate contentKey="intranetApp.matiere.idMatiere">Id Matiere</Translate>
            </span>
          </dt>
          <dd>{matiereEntity.idMatiere}</dd>
          <dt>
            <span id="nomMatiere">
              <Translate contentKey="intranetApp.matiere.nomMatiere">Nom Matiere</Translate>
            </span>
          </dt>
          <dd>{matiereEntity.nomMatiere}</dd>
          <dt>
            <span id="abreviation">
              <Translate contentKey="intranetApp.matiere.abreviation">Abreviation</Translate>
            </span>
          </dt>
          <dd>{matiereEntity.abreviation}</dd>
          <dt>
            <Translate contentKey="intranetApp.matiere.horaire">Horaire</Translate>
          </dt>
          <dd>
            {matiereEntity.horaires
              ? matiereEntity.horaires.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.idHoraire}</a>
                    {matiereEntity.horaires && i === matiereEntity.horaires.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
          <dt>
            <Translate contentKey="intranetApp.matiere.prof">Prof</Translate>
          </dt>
          <dd>
            {matiereEntity.profs
              ? matiereEntity.profs.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.login}</a>
                    {matiereEntity.profs && i === matiereEntity.profs.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/matiere" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/matiere/${matiereEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ matiere }: IRootState) => ({
  matiereEntity: matiere.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(MatiereDetail);
