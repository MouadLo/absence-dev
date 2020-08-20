import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './admin.reducer';
import { IAdmin } from 'app/shared/model/admin.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IAdminDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const AdminDetail = (props: IAdminDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { adminEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="intranetApp.admin.detail.title">Admin</Translate> [<b>{adminEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="login">
              <Translate contentKey="intranetApp.admin.login">Login</Translate>
            </span>
          </dt>
          <dd>{adminEntity.login}</dd>
          <dt>
            <span id="mdp">
              <Translate contentKey="intranetApp.admin.mdp">Mdp</Translate>
            </span>
          </dt>
          <dd>{adminEntity.mdp}</dd>
          <dt>
            <span id="email">
              <Translate contentKey="intranetApp.admin.email">Email</Translate>
            </span>
          </dt>
          <dd>{adminEntity.email}</dd>
          <dt>
            <span id="nom">
              <Translate contentKey="intranetApp.admin.nom">Nom</Translate>
            </span>
          </dt>
          <dd>{adminEntity.nom}</dd>
          <dt>
            <span id="prenom">
              <Translate contentKey="intranetApp.admin.prenom">Prenom</Translate>
            </span>
          </dt>
          <dd>{adminEntity.prenom}</dd>
          <dt>
            <span id="tel">
              <Translate contentKey="intranetApp.admin.tel">Tel</Translate>
            </span>
          </dt>
          <dd>{adminEntity.tel}</dd>
        </dl>
        <Button tag={Link} to="/admin" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/admin/${adminEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ admin }: IRootState) => ({
  adminEntity: admin.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(AdminDetail);
